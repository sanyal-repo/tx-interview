package com.learnvest.nmlv.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Institution {

  @Id
  private Long id;
  private String name;
  private String displayName;
  private String url;
  private Boolean isActive;

  protected Institution() {
  }

  private Institution(Long id, String name, String displayName, String url,
      Boolean isActive) {
    this.id = id;
    this.name = name;
    this.displayName = displayName;
    this.url = url;
    this.isActive = isActive;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getUrl() {
    return url;
  }

  public Boolean getActive() {
    return isActive;
  }

  public static Institution.Builder builder() {
    return new Institution.Builder();
  }

  public static final class Builder {

    private Long id;
    private String name;
    private String displayName;
    private String url;
    private Boolean isActive;

    public final Builder id(Long id) {
      this.id = id;
      return this;
    }

    public final Builder name(String name) {
      this.name = name;
      return this;
    }

    public final Builder displayName(String displayName) {
      this.displayName = displayName;
      return this;
    }

    public final Builder url(String url) {
      this.url = url;
      return this;
    }

    public final Builder active(Boolean active) {
      isActive = active;
      return this;
    }

    public Institution build() {
      return new Institution(id, name, displayName, url, isActive);
    }
  }
}
