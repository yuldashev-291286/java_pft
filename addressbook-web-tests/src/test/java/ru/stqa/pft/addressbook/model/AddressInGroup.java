package ru.stqa.pft.addressbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "address_in_groups")
public class AddressInGroup {

  @Id
  @Column(name = "domain_id")
  private int Id;

  @Column(name = "id")
  private int contactId;

  @Column(name = "group_id")
  private int groupId;

}
