package repository;


import domain.Wallet;
import domain.base.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface WalletRepository extends JpaRepository<Wallet,Long> {

    Wallet findWalletByUser(User user);
}