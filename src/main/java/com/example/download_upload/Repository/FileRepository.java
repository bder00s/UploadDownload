package com.example.download_upload.Repository;

import com.example.download_upload.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository <File, Long> {


}
