--Kailing Peng
--Alessia Vannini

/*
 * Fremdschluessel setzen
 */

 ALTER TABLE project 
 ADD CONSTRAINT fk_proj_team
   FOREIGN KEY (staff) REFERENCES team (name)
   ON DELETE SET NULL
 ;
   
 ALTER TABLE child_project
 ADD CONSTRAINT fk_child_proj2
   FOREIGN KEY(project_id) REFERENCES project(id)
   ON DELETE CASCADE
 ;

 ALTER TABLE donation 
 ADD CONSTRAINT fk_spons_don
   FOREIGN KEY (sponsor) REFERENCES sponsor (id),
 ADD CONSTRAINT fk_don_child
   FOREIGN KEY (to_child) REFERENCES child (id),
 ADD CONSTRAINT fk_don_proj
   FOREIGN KEY (to_project) REFERENCES project (id)
 ;