package id.msams.webrepo.srv.abs;

import javax.persistence.PersistenceException;

public class EntityExistingException extends PersistenceException {

  public EntityExistingException() {
    super();
  }

  public EntityExistingException(String message) {
    super(message);
  }

}
