package gallery.service;

import com.google.common.io.Files;
import engine.FeatureExtractor;
import engine.query.SimilarSearchQuery;
import gallery.model.Image;
import gallery.repository.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

@Service
public class ImagesService {
    @Autowired
    private ImagesRepository imagesRepository;
    private int SIMILAR_LENGTH = 6;//TODO: Подумать над тем, чтобы сделать число настраиваемым

    public Image getImageById(Long id) {
        return imagesRepository.getOne(id);
       //return imagesRepository.get(id);
    }

    public void save(Image img, MultipartFile imgFile) throws IOException {
        //добавить выделение признаков
        File tmp = new File("tmp");
        Files.write(imgFile.getBytes(), tmp);
        BufferedImage bimg = ImageIO.read(tmp);
        FeatureExtractor extractor = FeatureExtractor.getInstance();
        float[] features = extractor.extract(bimg, bimg.getWidth(), bimg.getHeight());
       // Image img =  new Image();
        img.setImage(imgFile.getBytes());
        img.setFeatures(features);
        imagesRepository.save(img);
    }

    public void delete(Long id) {
       // imagesRepository.remove(id);
        Image img = imagesRepository.getOne(id);
        imagesRepository.delete(img); //что произойдет, если id не найден?
    }

    public List<Image> getAllImages() {
        return imagesRepository.findAll();
    }

    public List<Image> findByText(String text){
        return imagesRepository.findByText(text);
    }

    public List<Image> findSimilar(Image center){
        List<Image> allImages = imagesRepository.findAll();
        SimilarSearchQuery query = new SimilarSearchQuery(allImages, SIMILAR_LENGTH);
        List<Image> similar = query.searchImages(center);
        return similar;
    }

    public List<Image> findSimilar(MultipartFile file) throws IOException{
        File tmp = new File("tmp");
        Files.write(file.getBytes(), tmp);
        BufferedImage bimg = ImageIO.read(tmp);

        FeatureExtractor extractor = FeatureExtractor.getInstance();
        List<Image> allImages = imagesRepository.findAll();
        float[] features = extractor.extract(bimg, bimg.getWidth(), bimg.getHeight());
        SimilarSearchQuery query = new SimilarSearchQuery(allImages, SIMILAR_LENGTH);
        List<Image> similar = query.searchImages(features);
        return similar;
    }

    public void save(MultipartFile imgFile) throws IOException{
        File tmp = new File("tmp");
        Files.write(imgFile.getBytes(), tmp);
        BufferedImage bimg = ImageIO.read(tmp);
        FeatureExtractor extractor = FeatureExtractor.getInstance();
        float[] features = extractor.extract(bimg, bimg.getWidth(), bimg.getHeight());
        Image img =  new Image();
        img.setImage(imgFile.getBytes());
        img.setFeatures(features);
        imagesRepository.save(img);
    }

}
