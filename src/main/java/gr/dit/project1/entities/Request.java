package gr.dit.project1.entities;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "requests")
public class Request {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime timestamp;

  private String sourceIp;
  
  private String type;

  @OneToMany(mappedBy = "request", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<Destination> destinations;

  @OneToMany(mappedBy = "request", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<Block> blocks;

  public Request() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public String getSourceIp() {
    return sourceIp;
  }

  public void setSourceIp(String sourceIp) {
    this.sourceIp = sourceIp;
  }

  public List<Destination> getDestinations() {
    return destinations;
  }

  public void setDestinations(List<Destination> destinations) {
    this.destinations = destinations;
  }

  public List<Block> getBlocks() {
    return blocks;
  }

  public void setBlocks(List<Block> blocks) {
    this.blocks = blocks;
  }

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

}
