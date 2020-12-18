package gallery.controller;

import gallery.model.Image;
import gallery.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

@RestController("/api/picture")
public class GalleryRestController {

    @Autowired
    ImagesService imagesService;
   // @Autowired
  //  private TagsService tagsService;
  /*  @PostMapping("/point")
    public int point(@RequestBody String url,
                     @RequestParam(value = "action", defaultValue = "plus") String action) {
        System.out.println(Long.valueOf(getIdFromUrl(url)));

        Long currentPicId = Long.valueOf(getIdFromUrl(url));
        Image pic = ps.getImageById(currentPicId);
        if (action.equals("plus"))
            pic.setPoints(pic.getPoints() + 1);
        if (action.equals("minus"))
            pic.setPoints(pic.getPoints() - 1);
        ps.update(pic, currentPicId);
        return pic.getPoints();
    }*/
  @RequestMapping(value = "image/{id}", method = RequestMethod.DELETE)
  public  ResponseEntity<Image> deleteImage(@RequestParam Long id) {
      HttpHeaders headers = new HttpHeaders();
      Image image = imagesService.getImageById(id);
      if (image == null) {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value= "/image/{id}", method=RequestMethod.GET)
  public ResponseEntity<Image> getImage(@RequestParam Long id){
      Image pic = imagesService.getImageById(id);
      if (pic == null)
      {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      HttpHeaders headers = new HttpHeaders();
      return new ResponseEntity<>(pic, headers, HttpStatus.FOUND);
  }
}