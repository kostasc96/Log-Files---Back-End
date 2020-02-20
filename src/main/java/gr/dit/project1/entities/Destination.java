package gr.dit.project1.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "destination")
public class Destination {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String destinationIp;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "request_id")
  private Request request;

  public Destination() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDestinationIp() {
    return destinationIp;
  }

  public void setDestinationIp(String destinationIp) {
    this.destinationIp = destinationIp;
  }

  public Request getRequest() {
    return request;
  }

  public void setRequest(Request request) {
    this.request = request;
  }

}
