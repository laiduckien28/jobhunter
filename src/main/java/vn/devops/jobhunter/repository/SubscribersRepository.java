package vn.devops.jobhunter.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.devops.jobhunter.domain.Subscribers;
public interface SubscribersRepository extends JpaRepository<Subscribers, Long>, JpaSpecificationExecutor<Subscribers>{
    public Subscribers findByName(String name);
}
