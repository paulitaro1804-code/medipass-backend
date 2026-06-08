package com.medipass.repository;

import com.medipass.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByUsuarioDestinoIdOrderByFechaEnvioDesc(String usuarioId);
    List<Notification> findByUsuarioDestinoIdAndLeidaFalse(String usuarioId);
    long countByUsuarioDestinoIdAndLeidaFalse(String usuarioId);
    Optional<Notification> findByPqrsIdAndUsuarioDestinoId(String pqrsId, String usuarioDestinoId);
}
