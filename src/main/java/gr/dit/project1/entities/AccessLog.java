package gr.dit.project1.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "access_log")
public class AccessLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String userId;

  private String method;

  @Column(columnDefinition = "text")
  private String resource;

  private Long response;

  @Column(columnDefinition = "text")
  private String referer;

  @Column(columnDefinition = "text")
  private String userAgent;

  @OneToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "request_id", referencedColumnName = "id")
  private Request requestId;

  public AccessLog() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public Long getResponse() {
    return response;
  }

  public void setResponse(Long response) {
    this.response = response;
  }

  public String getReferer() {
    return referer;
  }

  public void setReferer(String referer) {
    this.referer = referer;
  }

  public String getUserAgent() {
    return userAgent;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  public Request getRequestId() {
    return requestId;
  }

  public void setRequestId(Request requestId) {
    this.requestId = requestId;
  }

}
