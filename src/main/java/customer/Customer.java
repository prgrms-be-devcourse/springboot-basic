package customer;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class Customer {

  private final UUID id;
  private final String name;

  public Customer(UUID id, String name) {
    this.id = id;
    this.name = name;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String toString() {
    return "=============Customer==============" + System.lineSeparator() + "Id: " + id + System.lineSeparator()
        + "name: " + name + System.lineSeparator();
  }
}
