package ru.stqa.pft.rest.model;

public class Issue {

  private String id;
  private String subject;
  private String description;
  private int state;
  private String state_name;

  public String getNameState() {
    return state_name;
  }

  public Issue withNameState(String state_name) {
    this.state_name = state_name;
    return this;
  }

  public int getState() {
    return state;
  }

  public Issue withState(int state) {
    this.state = state;
    return this;
  }

  public String getId() {
    return id;
  }

  public Issue withId(String id) {
    this.id = id;
    return this;
  }

  public String getSubject() {
    return subject;
  }

  public Issue withSubject(String subject) {
    this.subject = subject;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Issue withDescription(String description) {
    this.description = description;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Issue issue = (Issue) o;

    if (state != issue.state) return false;
    if (id != null ? !id.equals(issue.id) : issue.id != null) return false;
    if (subject != null ? !subject.equals(issue.subject) : issue.subject != null) return false;
    if (description != null ? !description.equals(issue.description) : issue.description != null) return false;
    return state_name != null ? state_name.equals(issue.state_name) : issue.state_name == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (subject != null ? subject.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + state;
    result = 31 * result + (state_name != null ? state_name.hashCode() : 0);
    return result;
  }
}
