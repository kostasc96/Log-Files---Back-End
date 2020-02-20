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
@Table(name = "blocks")
public class Block {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String BlockId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "request_id")
  private Request request;

  public Block() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBlockId() {
    return BlockId;
  }

  public void setBlockId(String blockId) {
    BlockId = blockId;
  }

  public Request getRequest() {
    return request;
  }

  public void setRequest(Request request) {
    this.request = request;
  }

}
