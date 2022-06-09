package eus.evernature.evern.service.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ImageServiceImpl implements ImageService {

    // TODO: Change this path before deployment
    private final String IMAGE_FOLDER = "/uploads/";

    @Override
    public String saveImage(String image) {
        InputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(image.getBytes()));

        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            log.error("Could not read image from input stream");
            throw new RuntimeException("Could not read image from input stream");
        }

        String filename = getUniqueFileName();

        File file = new File(FileSystems.getDefault()
                .getPath(IMAGE_FOLDER, filename)
                .toString());

        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            log.error("Could not write image in " + file.getAbsolutePath());
            throw new RuntimeException("Could not write image in " + file.getAbsolutePath());
        }

        return file.getAbsolutePath();
    }

    @Override
    public String getUniqueFileName() {
        String filename = UUID.randomUUID().toString();
        String date = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new java.util.Date());
        String extension = ".png";

        return filename.concat(date).concat(extension);
    }

}
