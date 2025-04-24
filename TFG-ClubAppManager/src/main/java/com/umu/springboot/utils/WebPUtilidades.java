package com.umu.springboot.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.stereotype.Component;

import com.luciad.imageio.webp.WebPWriteParam;

@Component
public class WebPUtilidades {
    public byte[] convertirAWebP(byte[] imagenOriginal) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(imagenOriginal);
        BufferedImage imagen = ImageIO.read(in);
        ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream);
        writer.setOutput(ios);
        
        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        writeParam.setCompressionMode(WebPWriteParam.MODE_EXPLICIT);
        writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSY_COMPRESSION]);
        writeParam.setCompressionQuality(1.0f); 

        writer.write(null, new IIOImage(imagen, null, null), writeParam);
        ios.close();
        byte[] imageBytes2 = outputStream.toByteArray();
        return imageBytes2;
    }
    
    public BufferedImage descomprimirWebP(byte[] webpData) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(webpData);
        return ImageIO.read(inputStream);
    }
}

