package id.msams.webrepo.srv.abs;

import javax.persistence.PersistenceException;

public class EntityNotFoundException extends PersistenceException {

  public EntityNotFoundException() {
    super();
  }

  public EntityNotFoundException(String message) {
    super(message);
  }

}
