package service.impl;

import domain.Wallet;
import lombok.RequiredArgsConstructor;
import repository.WalletRepository;
import service.WalletService;

import java.util.List;
import java.util.Optional;

import static validation.EntityValidator.isValid;
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public Wallet saveOrUpdate(Wallet wallet) {
        if (!isValid(wallet))
            return null;
        return walletRepository.save(wallet);
    }

    @Override
    public String delete(Wallet wallet) {
         walletRepository.delete(wallet);
        return wallet.getUser().getUserName();
    }

    @Override
    public Optional<Wallet> findById(Long id) {
        return walletRepository.findById(id);
    }

    @Override
    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }


}
