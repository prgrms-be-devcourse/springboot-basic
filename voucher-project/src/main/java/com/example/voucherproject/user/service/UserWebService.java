package com.example.voucherproject.user.service;

import com.example.voucherproject.user.model.User;
import com.example.voucherproject.user.model.UserDTO;
import com.example.voucherproject.user.model.UserType;
import com.example.voucherproject.user.repository.UserRepository;
import com.example.voucherproject.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.voucherproject.user.model.UserDTO.asUserDTO;
import static com.example.voucherproject.user.model.UserDTO.asUserModel;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserWebService implements UserService{

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Override
    @Transactional
    public User createUser(User user) {
        return userRepository.insert(user);
    }

    @Override
    public List<User> findAll(){
        var users = userRepository.findAll();
        users.sort(Comparator.comparing(User::getCreatedAt));
        return users;
    }

    @Override
    public Optional<UserDTO> findById(UUID id) {
        var user = userRepository.findById(id);
        if(user.isPresent()){
            return Optional.of(asUserDTO(user.get()));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public boolean deleteById(UUID id){
        try{
            userRepository.deleteById(id);
            var wallets = walletRepository.findAllByUserId(id);
            wallets.forEach(wallet-> walletRepository.deleteById(wallet.getId()));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<User> findByTypeAndDate(UserType type, String from, String to) {
        var filteredUsers =  userRepository.findByTypeAndDate(type, from, to);
        filteredUsers.sort(Comparator.comparing(User::getCreatedAt));
        return filteredUsers;
    }
}
