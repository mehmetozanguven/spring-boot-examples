package com.mehmetozanguven.criteria_api_with_spring_boot.service;

import com.mehmetozanguven.criteria_api_with_spring_boot.entity.*;
import com.mehmetozanguven.criteria_api_with_spring_boot.model.UserDTO;
import com.mehmetozanguven.criteria_api_with_spring_boot.model.UserDynamicSearchSpecification;
import com.mehmetozanguven.criteria_api_with_spring_boot.repository.UserRepository;
import com.mehmetozanguven.criteria_api_with_spring_boot.repository.UserRoleRepository;
import com.mehmetozanguven.criteria_api_with_spring_boot.util.UserSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final EntityManager entityManager;

    public Page<User> findUserDynamically(UserDynamicSearchSpecification dynamicSearchSpecification, PageRequest pageRequest) {
        Specification<User> specification = UserSpecification.createSpecification(dynamicSearchSpecification);
        return userRepository.findAll(specification, pageRequest);
    }


    public Page<UserCountPerRole> countDistinctUsersPerRole(PageRequest pageRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserCountPerRole> query = criteriaBuilder.createQuery(UserCountPerRole.class);

        Metamodel metamodel = entityManager.getMetamodel();
        EntityType<User> userEntityType = metamodel.entity(User.class);
        Root<User> fromUserEntity = query.from(userEntityType);

        Join<User, UserRole> userRoleJoin = fromUserEntity.join(User_.userRoles, JoinType.LEFT);

        query.groupBy(userRoleJoin.get(UserRole_.role));

        query.select(
                criteriaBuilder.construct(UserCountPerRole.class,
                userRoleJoin.get(UserRole_.role),
                criteriaBuilder.countDistinct(fromUserEntity.get(User_.id)))
        );

        query.where(userRoleJoin.get(UserRole_.role).isNotNull());
        query.orderBy(criteriaBuilder.asc(userRoleJoin.get(UserRole_.role)));

        TypedQuery<UserCountPerRole> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageRequest.getOffset());
        typedQuery.setMaxResults(pageRequest.getPageSize());
        List<UserCountPerRole> content = typedQuery.getResultList();


        // Count query for pagination
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        Join<User, UserRole> countRoleJoin = countRoot.join(User_.userRoles);
        countQuery.select(criteriaBuilder.countDistinct(countRoleJoin.get(UserRole_.role)));
        countQuery.where(countRoleJoin.get(UserRole_.role).isNotNull());
        TypedQuery<Long> countQueryType = entityManager.createQuery(countQuery);
        Long totalElementsInPagination = countQueryType.getSingleResult();

        return new PageImpl<>(content, pageRequest, totalElementsInPagination);
    }

    public Page<UserDTO> getAllUserWithDTOProjection(PageRequest pageRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserDTO> query = criteriaBuilder.createQuery(UserDTO.class);

        Metamodel metamodel = entityManager.getMetamodel();
        EntityType<User> userEntityType = metamodel.entity(User.class);
        Root<User> fromUserEntity = query.from(userEntityType);

        query.select(
                criteriaBuilder.construct(UserDTO.class,
                fromUserEntity.get(User_.id).alias("id"),
                fromUserEntity.get(User_.email).alias("email"),
                fromUserEntity.get(User_.name).alias("name"),
                fromUserEntity.get(BaseEntity_.createTime).alias("create_time")
                ));

        query.orderBy(QueryUtils.toOrders(pageRequest.getSort(), fromUserEntity, criteriaBuilder));

        TypedQuery<UserDTO> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageRequest.getOffset());
        typedQuery.setMaxResults(pageRequest.getPageSize());
        List<UserDTO> content = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

        Root<User> countRoot = countQuery.from(userEntityType);
        countQuery.select(criteriaBuilder.count(countRoot));

        TypedQuery<Long> countQueryType = entityManager.createQuery(countQuery);
        Long totalElementsInPagination = countQueryType.getSingleResult();
        return new PageImpl<>(content, pageRequest, totalElementsInPagination);
    }

    public Page<User> getAllUsersWithJoin(PageRequest pageRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);

        Metamodel metamodel = entityManager.getMetamodel();
        EntityType<User> userEntityType = metamodel.entity(User.class);
        Root<User> fromUserEntity = query.from(userEntityType);

        fromUserEntity.fetch(User_.userRoles, JoinType.LEFT);
        fromUserEntity.fetch(User_.userAddresses, JoinType.LEFT);
        query.select(fromUserEntity);

        query.orderBy(QueryUtils.toOrders(pageRequest.getSort(), fromUserEntity, criteriaBuilder));

        TypedQuery<User> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageRequest.getOffset());
        typedQuery.setMaxResults(pageRequest.getPageSize());
        List<User> content = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

        Root<User> countRoot = countQuery.from(userEntityType);
        countQuery.select(criteriaBuilder.count(countRoot));

        TypedQuery<Long> countQueryType = entityManager.createQuery(countQuery);
        Long totalElementsInPagination = countQueryType.getSingleResult();

        return new PageImpl<>(content, pageRequest, totalElementsInPagination);
    }


    public Page<User> getAllUsersWithoutJoin(PageRequest pageRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);

        Metamodel metamodel = entityManager.getMetamodel();
        EntityType<User> userEntityType = metamodel.entity(User.class);
        Root<User> fromUserEntity = query.from(userEntityType);

        query.select(fromUserEntity);
        query.orderBy(QueryUtils.toOrders(pageRequest.getSort(), fromUserEntity, criteriaBuilder));

        TypedQuery<User> typedQuery = entityManager.createQuery(query);

        typedQuery.setFirstResult((int) pageRequest.getOffset());
        typedQuery.setMaxResults(pageRequest.getPageSize());
        List<User> content = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

        Root<User> countRoot = countQuery.from(userEntityType);
        countQuery.select(criteriaBuilder.count(countRoot));

        TypedQuery<Long> countQueryType = entityManager.createQuery(countQuery);
        Long totalElementsInPagination = countQueryType.getSingleResult();

        return new PageImpl<>(content, pageRequest, totalElementsInPagination);
    }

    /**
     * Sql Query:["
     *     select
     *         u1_0.id,
     *         u1_0.email,
     *         u1_0.name,
     *         u1_0.version_id
     *     from
     *         users u1_0"]
     * @return
     */
    public List<User> getAllUsers() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> user = query.from(User.class);
        query.select(user);

        TypedQuery<User> typedQuery = entityManager.createQuery(query);
        List<User> allUsers = typedQuery.getResultList();
        return allUsers;
    }

    /**
     * Sql Query:["
     *     select
     *         u1_0.name
     *     from
     *         users u1_0"]
     * @return
     */
    public List<String> getAllUsersEmails() {
        // get builder to build query
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // create query with return type String
        CriteriaQuery<String> query = criteriaBuilder.createQuery(String.class);

        // using metamodel
        // set query's "FROM" clause e.g ... FROM User
        Metamodel metamodel = entityManager.getMetamodel();
        EntityType<User> user_ = metamodel.entity(User.class);
        Root<User> user = query.from(user_);

        // set query's select value(s), this should be matched with return type of the CriteriaQuery
        query.select(user.get(User_.name));

        // Prepare the query for execution, specify the query result type
        TypedQuery<String> typedQuery = entityManager.createQuery(query);

        // execute the query
        List<String> allUsers = typedQuery.getResultList();
        return allUsers;
    }

    /**
     * Sql Query:["
     *     select
     *         u1_0.id,
     *         u1_0.email,
     *         u1_0.name,
     *         u1_0.version_id
     *     from
     *         users u1_0
     *     where
     *         u1_0.name is not null"]
     * @return
     */
    public List<User> findAllUsersWhereNameNotNull() {
        // get builder to build query
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        // using metamodel
        // set query's "FROM" clause e.g ... FROM User
        Metamodel metamodel = entityManager.getMetamodel();
        EntityType<User> user_ = metamodel.entity(User.class);
        Root<User> user = query.from(user_);

        // set query's where clause
        query.where(user.get(User_.name).isNotNull());

        // Prepare the query for execution, specify the query result type
        TypedQuery<User> typedQuery = entityManager.createQuery(query);

        // execute the query
        return typedQuery.getResultList();
    }

    /**
     * Sql Query:["
     *     select
     *         u1_0.id,
     *         u1_0.email,
     *         u1_0.name,
     *         u1_0.version_id
     *     from
     *         users u1_0
     *     where
     *         u1_0.email like ? escape ''"]
     * Params:[(%customer%)]
     * @return
     */
    public List<User> getUsersWithEmailLike(String email) {
        // get builder to build query
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        // using metamodel
        // set query's "FROM" clause e.g ... FROM User
        Metamodel metamodel = entityManager.getMetamodel();
        EntityType<User> user_ = metamodel.entity(User.class);
        Root<User> user = query.from(user_);

        // set query's where clause (use additional expression from CriteriaBuilder
        query.where(criteriaBuilder.like(user.get(User_.email), "%" + email + "%"));

        // Prepare the query for execution, specify the query result type
        TypedQuery<User> typedQuery = entityManager.createQuery(query);

        // execute the query
        return typedQuery.getResultList();
    }
}
