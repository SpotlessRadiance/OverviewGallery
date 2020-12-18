package gallery.controller;

import engine.FeatureExtractor;
import gallery.model.Image;
import gallery.model.User;
import gallery.service.ImagesService;
import gallery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("") // /images
public class ImagesController {
    @Autowired
    private ImagesService imagesService;

    @Autowired
    private UserService usersService;


    @GetMapping({""})
    public String showAll(Model model){
        model.addAttribute("images", imagesService.getAllImages());
        model.addAttribute("header","Галерея");
        return "gallery";
    }


    @GetMapping("/show/{imgId}")
    public String showImage(@PathVariable Long imgId, Model model){
        Image img =  imagesService.getImageById(imgId);
        //prevImg, nextImg = imagesService.getImagePrevNext(imgId);
        model.addAttribute("image",img);
        return "view";
    }

    @GetMapping("/search")
    public String uploadSearchSimilar(Model model){
       // Image image = imagesService.getImageById(id);
       // model.addAttribute("image", image);
        return "search.html";
    }

    @PostMapping("/search")
    public String searchSimilar(@RequestParam("file") MultipartFile file, Model model){
        try {
            List<Image> similar = imagesService.findSimilar(file);
            model.addAttribute("similar", similar);
        }
        catch (IOException e){
            model.addAttribute("error","Что-то пошло не так");
        }
        return "search.html";
    }

    @GetMapping("/search/{id}")
    public String searchSimilar(@PathVariable Long id, Model model){
        Image image = imagesService.getImageById(id);
         List<Image> similar = imagesService.findSimilar(image);
         model.addAttribute("similar", similar);
        model.addAttribute("image", image);
        return "search.html";
    }

    @GetMapping("/upload")
    public String uploadImage(Model model){
        model.addAttribute("picture", new Image());
        return "upload";
    }

    @PostMapping("/upload")
    public String add(@RequestParam("file") MultipartFile file,
                      @Valid @ModelAttribute("picture") Image img,
                      BindingResult result,
                      Model model) {
        try {
            if (result.hasErrors())
                throw new Exception();
            if (file.getSize() > 1048576)
                throw new IOException("Максимальный размер - 10Мб");          //10Mb max
            UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();

            User userEntity = usersService.findByEmail(user.getUsername());
            img.setAuthor(userEntity);
            imagesService.save(img, file);
        }
        catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Что-то пошло не так");
            return "upload";
        }
        model.addAttribute("uploadedImage", img);
        model.addAttribute("success", "Успешно загружено!");
        return "upload";
    }

    @RequestMapping(value = "/search-by-name/image", method = RequestMethod.GET)
    public String searchByName(@RequestParam String text, Model model){
        List<Image> images = imagesService.findByText(text);
        text="Найдено по запросу: "+text;
        model.addAttribute("header", text);
        model.addAttribute("images", images);
        return "gallery";
    }
}

/*
public ResponseEntity<Photo> createPhoto(@Valid @RequestBody Photo photo) throws Exception {
        log.debug("REST request to save Photo : {}", photo);
        if (photo.getId() != null) {
            throw new BadRequestAlertException("A new photo cannot already have an ID", ENTITY_NAME, "idexists");
        }

        try {
            photo = setMetadata(photo);
        } catch (ImageProcessingException ipe) {
            log.error(ipe.getMessage());
        }

        Photo result = photoRepository.save(photo);
        return ResponseEntity.created(new URI("/api/photos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    private Photo setMetadata(Photo photo) throws ImageProcessingException, IOException, MetadataException {
        String str = DatatypeConverter.printBase64Binary(photo.getImage());
        byte[] data2 = DatatypeConverter.parseBase64Binary(str);
        InputStream inputStream = new ByteArrayInputStream(data2);
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        Metadata metadata = ImageMetadataReader.readMetadata(bis);
        ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

        if (directory != null) {
            Date date = directory.getDateDigitized();
            if (date != null) {
                photo.setTaken(date.toInstant());
            }
        }

        if (photo.getTaken() == null) {
            log.debug("Photo EXIF date digitized not available, setting taken on date to now...");
            photo.setTaken(Instant.now());
        }

        photo.setUploaded(Instant.now());

        JpegDirectory jpgDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
        if (jpgDirectory != null) {
            photo.setHeight(jpgDirectory.getImageHeight());
            photo.setWidth(jpgDirectory.getImageWidth());
        }

        return photo;
    }
https://github.com/oktadeveloper/okta-react-photo-gallery-example/blob/master/src/main/java/com/okta/developer/web/rest/PhotoResource.java
 */