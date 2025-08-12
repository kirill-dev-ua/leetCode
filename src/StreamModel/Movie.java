package StreamModel;

import java.util.List;

public record Movie(int id, String title, int year, List<Integer> actorIds, double rating) {}
