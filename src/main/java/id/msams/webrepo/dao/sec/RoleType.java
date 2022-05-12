package id.msams.webrepo.dao.sec;

public enum RoleType {

  SUPER_ADMIN,
  ADMIN,
  STUDENT,
  LECTURER,

  ;

  public String roleName() {
    return this.name();
  }

  public String authorityName() {
    return "ROLE_" + this.roleName();
  }

}
