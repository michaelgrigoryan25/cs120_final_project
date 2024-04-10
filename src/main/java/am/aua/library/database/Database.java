package am.aua.library.database;

import am.aua.library.dto.ProfessorRegistrationDto;
import am.aua.library.dto.StudentRegistrationDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Database class provides methods for managing users and institutions.
 */
public class Database {
    /**
     * Initializing a Gson instance for serialization and deserialization
     */
    private final static Gson serializer = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    /**
     * Base directory for storing database files
     */
    protected static String directory = null;

    /**
     * Returns the base directory of the database
     *
     * @return The base directory of the database
     */
    public static String getBaseDirectory() {
        return directory;
    }

    /**
     * Sets the base directory for storing database files.
     *
     * @param path Base directory path
     * @throws DatabaseException If directory creation fails or directory is null
     */
    public synchronized static void setBaseDirectory(String path) throws DatabaseException {
        if (path == null) {
            throw new DatabaseException("Directory is not set and is currently `null`");
        }

        File directory = Path.of(path).toFile();
        if (!directory.exists()) {
            boolean created = directory.mkdir();
            if (!created) {
                throw new DatabaseException("Unable to create directory " + directory);
            }
        }

        Database.directory = path;
    }

    /**
     * Creates a new record file.
     *
     * @param path Relative path of the file within the base directory
     * @return The created file
     * @throws DatabaseException If file creation fails or path is null
     */
    public synchronized static File createNewRecord(Path path) throws DatabaseException {
        if (path != null) {
            path = Path.of(directory, path.toString());
            File file = new File(path.toString());
            if (!file.exists()) {
                try {
                    boolean created = file.createNewFile();
                    if (!created) {
                        throw new DatabaseException("Could not create file: " + path);
                    }

                    return file;
                } catch (IOException e) {
                    throw new DatabaseException(e.getMessage());
                }
            }

            return file;
        } else {
            throw new DatabaseException("Provided path is `null`");
        }
    }

    public synchronized static StudentRegistrationDto saveStudent(StudentRegistrationDto dto, String fileName) throws DatabaseException{
        try {
            // add the provided data transfer object to the json file with the provided fileName
            System.out.println(serializer.toJson(dto));
            serializer.toJson(dto, new FileWriter(directory + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new DatabaseException("Could not save the student with the following data: " + dto);
        }
        // if successful, return the provided data transfer object
        return dto;
    }

    public synchronized static ProfessorRegistrationDto saveProfessor(ProfessorRegistrationDto dto, String fileName) throws DatabaseException {
        try {
            System.out.println(serializer.toJson(dto));
            serializer.toJson(dto, new FileWriter(directory + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new DatabaseException("Could not save the teacher with the following data: " + dto);
        }
        return dto;
    }

}
