//package com.example.SpringBoard.service;
//
//import com.example.SpringBoard.entity.Post;
//import com.example.SpringBoard.entity.User;
//import com.example.SpringBoard.exceptions.DataNotFoundException;
//import com.example.SpringBoard.repository.PostRepository;
//import jakarta.persistence.criteria.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@Service
//public class PostService_old {
//    private final PostRepository postRepository;
//
//    public Post create(String title,String writer, String password, String text){
//        Post post = new Post();
//        post.setTitle(title);
//        post.setWriter(writer);
//        post.setPassword(password);
//        post.setText(text);
//        this.postRepository.save(post);
//        return post;
//    }
//
//    public List<Post> findAll() {
//        return this.postRepository.findAll();
//    }
//
//    public Page<Post> getPage(int page, int sort, String kw, String option){
//
//        Sort sorting = switch (sort) {
//            case 1 -> Sort.by("view").descending();
//            case 2 -> Sort.by("title").ascending();
//            default -> Sort.by("date").descending();
//        };
//        Pageable pageable = PageRequest.of(page, 10,sorting);
//        Specification<Post> spec = search(kw,option);
//        return this.postRepository.findAll(spec,pageable);
//    }
//
//    public Post getPost(Integer id) {
//        Optional<Post> post = this.postRepository.findById(id);
//        if (post.isPresent()) {
//            Post temp = post.get();
//            temp.setView(temp.getView()+1);
//            this.postRepository.save(temp);
//            return post.get();
//        } else {
//            throw new DataNotFoundException("post not found");
//        }
//    }
//
//    public void delete(Post post) {
//        this.postRepository.delete(post);
//    }
//
//    public void edit(Post post, String title, String text){
//        post.setTitle(title);
//        post.setText(text);
//        this.postRepository.save(post);
//    }
//
//    private Specification<Post> search(String kw,String option){
//        return new Specification<Post>() {
//            @Override
//            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                query.distinct(true);
//                Join<Post, User> join = root.join("user", JoinType.LEFT);
//                if(option.equals("user") ){
//                    return criteriaBuilder.or(criteriaBuilder.like(join.get("username"), "%" + kw + "%"));
//                }else{
//                    return criteriaBuilder.or(criteriaBuilder.like(root.get(option), "%" + kw + "%"));
//                }
//            }
//        };
//    }
//}
