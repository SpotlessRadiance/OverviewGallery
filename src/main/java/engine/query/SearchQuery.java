package engine.query;

import gallery.model.Image;

import java.util.ArrayList;
import java.util.List;

public interface SearchQuery {
    List<Image> searchImages(Image target);
    List<Image> searchImages(float[] features);
}
