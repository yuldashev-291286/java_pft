package ru.stqa.pft.rest.model;

public class Issue {

  private int Id;
  private String subject;
  private String description;
  private String state;
  private String nameState;

  public String getNameState() {
    return nameState;
  }

  public Issue withNameState(String nameState) {
    this.nameState = nameState;
    return this;
  }

  public String getState() {
    return state;
  }

  public Issue withState(String state) {
    this.state = state;
    return this;
  }

  public int getId() {
    return Id;
  }

  public Issue withId(int id) {
    Id = id;
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

    if (Id != issue.Id) return false;
    if (subject != null ? !subject.equals(issue.subject) : issue.subject != null) return false;
    if (description != null ? !description.equals(issue.description) : issue.description != null) return false;
    if (state != null ? !state.equals(issue.state) : issue.state != null) return false;
    return nameState != null ? nameState.equals(issue.nameState) : issue.nameState == null;
  }

  @Override
  public int hashCode() {
    int result = Id;
    result = 31 * result + (subject != null ? subject.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (state != null ? state.hashCode() : 0);
    result = 31 * result + (nameState != null ? nameState.hashCode() : 0);
    return result;
  }
}
