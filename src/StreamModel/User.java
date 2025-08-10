package StreamModel;

import java.util.List;

public record User(String name, List<Transaction> transactions) {}
