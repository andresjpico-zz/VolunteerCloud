package com.mycompany.controllers;

import com.mycompany.entityclasses.Users;
import com.mycompany.entityclasses.VolunteeringOpportunities;
import com.mycompany.entityclasses.Photo;
import com.mycompany.entityclasses.Constants;
import com.mycompany.sessionbeans.UsersFacade;
import com.mycompany.sessionbeans.VolunteeringOpportunitiesFacade;
import com.mycompany.sessionbeans.PhotoFacade;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Named;
import org.imgscalr.Scalr;
import org.primefaces.model.UploadedFile;

@Named(value = "fileManager")
@SessionScoped
/**
 *
 * @author megh
 * Source: Apartmates cloud software application
 */
public class FileManager implements Serializable {

    // Instance Variables (Properties)
    private UploadedFile file;
    private String statusMessage = "";

    @EJB
    private UsersFacade usersFacade;
    
    @EJB 
    private VolunteeringOpportunitiesFacade opportunityFacade;
    
    /*
    The @EJB annotation implies that the EJB container will perform an injection of the object
    reference of the PhotoFacade object into photoFacade when it is created at runtime.
     */
    @EJB
    private PhotoFacade photoFacade;

    // Returns the uploaded file
    public UploadedFile getFile() {
        return file;
    }

    // Set the uploaded file
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    // Return the message
    public String getStatusMessage() {
        return statusMessage;
    }

    // Set the message
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    // Handle the upload of the selected file
    public String upload() {

        // Check if a file is selected
        if (file.getSize() == 0) {
            statusMessage = "You need to choose a file first!";
            return "";
        }

        /*
        MIME (Multipurpose Internet Mail Extensions) is a way of identifying files on
        the Internet according to their nature and format. 

        A "Content-type" is simply a header defined in many protocols, such as HTTP, that 
        makes use of MIME types to specify the nature of the file currently being handled.

        Some MIME file types: (See http://www.freeformatter.com/mime-types-list.html)

            JPEG Image      = image/jpeg or image/jpg
            PNG image       = image/png
            Plain text file = text/plain
            HTML file       = text/html
            JSON file       = application/json
         */
        // Obtain the uploaded file's MIME file type
        String mimeFileType = file.getContentType();

        if (mimeFileType.startsWith("image/")) {
            // The uploaded file is an image file
            /*
            The subSequence() method returns the portion of the mimeFileType string from the 6th
            position to the last character. Note that it starts with "image/" which has 6 characters at
            positions 0,1,2,3,4,5. Therefore, we start the subsequence at position 6 to obtain the file extension.
             */
            String fileExtension = mimeFileType.subSequence(6, mimeFileType.length()).toString();

            String fileExtensionInCaps = fileExtension.toUpperCase();

            if (fileExtensionInCaps.endsWith("JPG") || fileExtensionInCaps.endsWith("JPEG") || fileExtensionInCaps.endsWith("PNG")) {
                // File type is acceptable
            } else {
                statusMessage = "The uploaded file type is not a JPG, JPEG or PNG!";
                return "";
            }
        } else {
            statusMessage = "The uploaded file must be an image file of type JPG, JPEG or PNG!";
            return "";
        }

        storePhotoFile(file);
        statusMessage = "";

        // Redirect to show the Profile page
        return "Profile?faces-redirect=true";
    }

    // Handle the upload of the selected file
    public String uploadOpportunityPhoto() {

        // Check if a file is selected
        if (file.getSize() == 0) {
            statusMessage = "You need to choose a file first!";
            return "";
        }

        // Obtain the uploaded file's MIME file type
        String mimeFileType = file.getContentType();

        if (mimeFileType.startsWith("image/")) {
            // The uploaded file is an image file
            /*
            The subSequence() method returns the portion of the mimeFileType string from the 6th
            position to the last character. Note that it starts with "image/" which has 6 characters at
            positions 0,1,2,3,4,5. Therefore, we start the subsequence at position 6 to obtain the file extension.
             */
            String fileExtension = mimeFileType.subSequence(6, mimeFileType.length()).toString();

            String fileExtensionInCaps = fileExtension.toUpperCase();

            if (fileExtensionInCaps.endsWith("JPG") || fileExtensionInCaps.endsWith("JPEG") || fileExtensionInCaps.endsWith("PNG")) {
                // File type is acceptable
            } else {
                statusMessage = "The uploaded file type is not a JPG, JPEG or PNG!";
                return "";
            }
        } else {
            statusMessage = "The uploaded file must be an image file of type JPG, JPEG or PNG!";
            return "";
        }

        storeOpportunityPhotoFile(file);
        statusMessage = "";

        // Redirect to show the Profile page
        return "EditOpportunity?faces-redirect=true";
    }
    
    // Cancel file upload
    public String cancel() {
        statusMessage = "";
        return "Profile?faces-redirect=true";
    }
    
    // Store the uploaded photo file and its thumbnail version and create a database record 
    public FacesMessage storePhotoFile(UploadedFile file) {
        try {

            // Delete logged-in roommate's uploaded photo file, its thumbnail file, tmp_file, and its database record.
            deletePhoto();

            /*
            InputStream is an abstract class, which is the superclass of all classes representing an input stream of bytes.
            Convert the uploaded file into an input stream of bytes.
             */
            InputStream inputStream = file.getInputstream();

            // Write the uploaded file's input stream of bytes into the file named TEMP_FILE = "tmp_file"
            File tempFile = inputStreamToFile(inputStream, Constants.TEMP_FILE);

            // Close the input stream and release any system resources associated with it
            inputStream.close();

            FacesMessage resultMsg;

            // Obtain the username of the logged-in roommate
            Integer userID = (int) FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap().get("userID");

            // Obtain the object reference of the logged-in roommate object
            Users user = usersFacade.findByID(userID);

            // Obtain the uploaded file's MIME file type
            String mimeFileType = file.getContentType();

            // If it is an image file, obtain its file extension; otherwise, set png as the file extension anyway.
            String fileExtension = mimeFileType.startsWith("image/") ? mimeFileType.subSequence(6, mimeFileType.length()).toString() : "png";

            /*
            Obtain the list of Photo objects that belong to the roommate whose
            database primary key is roommate.getroommateID()
             */
            Photo photo = photoFacade.findPhotoByUserID(user.getUserID());

            if (photo != null) {
                // Remove the photo from the database
                photoFacade.remove(photo);
            }

            // Construct a new Photo object with file extension and roommate's object reference
            Photo newPhoto = new Photo(fileExtension, user);

            // Create a record for the new Photo object in the database
            photoFacade.create(newPhoto);

            photo = photoFacade.findPhotoByUserID(user.getUserID());

            // Reconvert the uploaded file into an input stream of bytes.
            inputStream = file.getInputstream();

            // Write the uploaded file's input stream of bytes under the photo object's
            // filename using the inputStreamToFile method given below
            File uploadedFile = inputStreamToFile(inputStream, photo.getFilename());

            // Create and save the thumbnail version of the uploaded file
            saveThumbnail(uploadedFile, photo);

            // Compose the result message
            resultMsg = new FacesMessage("Success!", "File Successfully Uploaded!");

            // Return the result message
            return resultMsg;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new FacesMessage("Upload failure!",
                "There was a problem reading the image file. Please try again with a new photo file.");
    }
    
    // Store the uploaded photo file and its thumbnail version and create a database record 
    public FacesMessage storeOpportunityPhotoFile(UploadedFile file) {
        try {

            // Delete logged-in roommate's uploaded photo file, its thumbnail file, tmp_file, and its database record.
            deleteOpportunityPhoto();

            /*
            InputStream is an abstract class, which is the superclass of all classes representing an input stream of bytes.
            Convert the uploaded file into an input stream of bytes.
             */
            InputStream inputStream = file.getInputstream();

            // Write the uploaded file's input stream of bytes into the file named TEMP_FILE = "tmp_file"
            File tempFile = inputStreamToFile(inputStream, Constants.TEMP_FILE);

            // Close the input stream and release any system resources associated with it
            inputStream.close();

            FacesMessage resultMsg;

            // Obtain the username of the logged-in roommate
            Integer userID = (int) FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap().get("userID");
            Integer opportunityID = (int) FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap().get("opportunityID");

            // Obtain the uploaded file's MIME file type
            String mimeFileType = file.getContentType();

            // If it is an image file, obtain its file extension; otherwise, set png as the file extension anyway.
            String fileExtension = mimeFileType.startsWith("image/") ? mimeFileType.subSequence(6, mimeFileType.length()).toString() : "png";

            /*
            Obtain the list of Photo objects that belong to the roommate whose
            database primary key is roommate.getroommateID()
             */
            Photo photo = photoFacade.findPhotoByOpportunityID(opportunityID);

            if (photo != null) {
                // Remove the photo from the database
                photoFacade.remove(photo);
            }

            // Construct a new Photo object with file extension and roommate's object reference
            Photo newPhoto = new Photo(fileExtension, userID, opportunityID);

            // Create a record for the new Photo object in the database
            photoFacade.create(newPhoto);

            photo = photoFacade.findPhotoByOpportunityID(opportunityID);

            // Reconvert the uploaded file into an input stream of bytes.
            inputStream = file.getInputstream();

            // Write the uploaded file's input stream of bytes under the photo object's
            // filename using the inputStreamToFile method given below
            File uploadedFile = inputStreamToFile(inputStream, photo.getFilename());

            // Create and save the thumbnail version of the uploaded file
            saveThumbnail(uploadedFile, photo);
            
            // Removes selected opportunityID from session map as picture has been saved by now
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("opportunityID");
            
            // Compose the result message
            resultMsg = new FacesMessage("Success!", "File Successfully Uploaded!");

            // Return the result message
            return resultMsg;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new FacesMessage("Upload failure!",
                "There was a problem reading the image file. Please try again with a new photo file.");
    }

    /**
     * @param inputStream of bytes to be written into file with name targetFilename
     * @param targetFilename
     * @return the created file targetFile
     * @throws IOException
     */
    private File inputStreamToFile(InputStream inputStream, String targetFilename) throws IOException {

        /*
        inputStream.available() returns an estimate of the number of bytes that can be read from
        the inputStream without blocking by the next invocation of a method for this input stream.
        A memory buffer of bytes is created with the size of estimated number of bytes.
         */
        byte[] buffer = new byte[inputStream.available()];

        // Read the bytes of data from the inputStream into the created memory buffer. 
        inputStream.read(buffer);

        // Create targetFile with the given targetFilename in the ROOT_DIRECTORY    
        File targetFile = new File(Constants.ROOT_DIRECTORY, targetFilename);

        // A file OutputStream is an output stream for writing data to a file
        OutputStream outStream;

        /*
        FileOutputStream is intended for writing streams of raw bytes such as image data.
        Create a new FileOutputStream for writing to targetFile
         */
        outStream = new FileOutputStream(targetFile);

        // Write the inputStream from the memory buffer into the targetFile
        outStream.write(buffer);

        // Close the output stream and release any system resources associated with it. 
        outStream.close();

        // Return the created file targetFile
        return targetFile;
    }

    /*
    When a roommate uploads a photo, a thumbnail (small) version of the photo image
    is created in this method by using the Scalr.resize method provided in the
    imgscalr (Java Image Scaling Library) imported as imgscalr-lib-4.2.jar
     */
    private void saveThumbnail(File inputFile, Photo inputPhoto) {
        try {
            // Buffer the photo image from the uploaded inputFile
            BufferedImage uploadedPhoto = ImageIO.read(inputFile);

            // Scale the uploaded photo image to the THUMBNAIL_SIZE using imgscalr 
            BufferedImage thumbnailPhoto = Scalr.resize(uploadedPhoto, Scalr.Mode.FIT_EXACT, Constants.THUMBNAIL_SIZE, Constants.THUMBNAIL_SIZE);
            
            // Create the thumbnail photo file in the ROOT_DIRECTORY
            File thumbnailPhotoFile = new File(Constants.ROOT_DIRECTORY, inputPhoto.getThumbnailName());

            // Write the thumbnailPhoto into thumbnailPhotoFile with the file extension
            ImageIO.write(thumbnailPhoto, inputPhoto.getExtension(), thumbnailPhotoFile);

        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    Delete uploaded photo, thumbnail photo, and tmp_file that belong to
    the logged-in roommate object and remove the photo's database record.
     */
    public void deletePhoto() {

        FacesMessage resultMsg;

        int userID = (int) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("userID");

        Users user = usersFacade.findByID(userID);

        Photo photo = photoFacade.findPhotoByUserID(user.getUserID());

        if (photo == null) {

            resultMsg = new FacesMessage("Error", "You do not have a photo to delete.");

        } else {
            // Obtain the object reference of the first Photo object in the list
            try {
                // Delete the uploaded photo file if it exists
                Files.deleteIfExists(Paths.get(photo.getFilePath()));

                // Delete the thumbnail image file if it exists
                Files.deleteIfExists(Paths.get(photo.getThumbnailFilePath()));

                // Delete the temporary file if it exists
                Files.deleteIfExists(Paths.get(Constants.ROOT_DIRECTORY + "tmp_file"));

                photoFacade.remove(photo);

            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }

            resultMsg = new FacesMessage("Success", "Photo successfully deleted!");
        }
        FacesContext.getCurrentInstance().addMessage(null, resultMsg);
    }
    
    /*
    Delete uploaded photo, thumbnail photo, and tmp_file that belong to
    the logged-in roommate object and remove the photo's database record.
     */
    public void deleteOpportunityPhoto() {

        FacesMessage resultMsg;

        int opportunityID = (int) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("opportunityID");

        VolunteeringOpportunities opportunity = opportunityFacade.findByOpportunityID(opportunityID);
        Photo photo = photoFacade.findPhotoByOpportunityID(opportunity.getOpportunityID());

        if (photo == null) {

            resultMsg = new FacesMessage("Error", "The opportunity does not have a photo to delete.");

        } else {
            // Obtain the object reference of the first Photo object in the list
            try {
                // Delete the uploaded photo file if it exists
                Files.deleteIfExists(Paths.get(photo.getFilePath()));

                // Delete the thumbnail image file if it exists
                Files.deleteIfExists(Paths.get(photo.getThumbnailFilePath()));

                // Delete the temporary file if it exists
                Files.deleteIfExists(Paths.get(Constants.ROOT_DIRECTORY + "tmp_file"));

                photoFacade.remove(photo);

            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }

            resultMsg = new FacesMessage("Success", "Photo successfully deleted!");
        }
        FacesContext.getCurrentInstance().addMessage(null, resultMsg);
    }
}
