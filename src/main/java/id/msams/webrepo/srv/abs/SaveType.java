package id.msams.webrepo.srv.abs;

public enum SaveType {

  /**
   * Save if and only if there is no existing object.
   * Inserts new record.
   */
  INSERT,
  /**
   * Insert if there is no existing object,
   * or update if there is existing object.
   */
  UPSERT,
  /**
   * Delete existing object if any,
   * and then insert new object regardless.
   */
  DELSERT,
  /**
   * Save if and only if there is existing object.
   * Replaces existing record.
   */
  UPDATE,
  /**
   * Save if and only if there is existing object.
   * Updates fields of existing record selectively.
   */
  PARTIAL

  ;

}
