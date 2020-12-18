package engine.query;

import gallery.model.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SimilarSearchQuery implements SearchQuery {
    private List<Image> allImages;
    private int answerLength;

    public SimilarSearchQuery(List<Image> allImages, int answerLength) {
        this.allImages = allImages;
        this.answerLength = answerLength;
    }

    public int getAnswerLength() {
        return answerLength;
    }

    @Override
    public List<Image> searchImages(float[] features){//, boolean onlySimilar) {
        ArrayList<Image> images = new ArrayList<>();
        ArrayList<SearchElement<Image>> queryElements = formDistances(features, -1);
        queryElements.sort(Collections.reverseOrder(Comparator.comparingDouble(element -> element.getDistance())));
        answerLength = queryElements.size() < answerLength ? queryElements.size() : answerLength;
        for (int i =0; i <answerLength; i++) {
           if (queryElements.get(i).getDistance() < 0.7)
                break;
            images.add(queryElements.get(i).getElement());
        }
        return images;
    }

    @Override
    public List<Image> searchImages(Image target){//, boolean onlySimilar) {
        ArrayList<Image> images = new ArrayList<>();
        float[] centerFeatures = target.getFeatures();
        ArrayList<SearchElement<Image>> queryElements = formDistances(centerFeatures, target.getId());
        queryElements.sort(Collections.reverseOrder(Comparator.comparingDouble(element -> element.getDistance())));

        for (int i =0; i <answerLength; i++) {
             if (queryElements.get(i).getDistance() < 0.7)
                break;
            images.add(queryElements.get(i).getElement());
        }
        return images;
    }

    private ArrayList<SearchElement<Image>> formDistances(float[] centerFeatures, long centerId) {
        ArrayList<SearchElement<Image>> queryElements = new ArrayList<>();
        Image current;
        for (int i = 0; i < allImages.size(); i++) {
            current = allImages.get(i);
            if (current.getId() == centerId)
                continue;
            float[] currentFeatures = current.getFeatures();
            float distance = calculateDistance(centerFeatures, currentFeatures);
            SearchElement<Image> newElement = new SearchElement<>(current, distance);
            queryElements.add(newElement);
        }
        return queryElements;
    }

    private float calculateDistance(float[] centerCoordinates, float[] elementCoordinates) {
       if (centerCoordinates.length != elementCoordinates.length)
            throw new IllegalArgumentException("Vectors have different lengths");
        int length = centerCoordinates.length;
        float dotProduct = 0;
        float vectorCenter = 0;
        float vectorElement = 0;
        for (int i=0; i<length; i++){
            dotProduct += centerCoordinates[i] * elementCoordinates[i];
            vectorCenter += centerCoordinates[i] * centerCoordinates[i];
            vectorElement += elementCoordinates[i] * elementCoordinates[i];
        }
        vectorCenter = (float)Math.sqrt(vectorCenter);
        vectorElement = (float)Math.sqrt(vectorElement);
        float distance = dotProduct/(vectorCenter*vectorElement);
      return distance;
    }
}
