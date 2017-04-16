/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.broodcamp.javaee_querydsl_demo.data;

import static com.broodcamp.javaee_querydsl_demo.model.QMember.member;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.broodcamp.javaee_querydsl_demo.model.Member;
import com.broodcamp.javaee_querydsl_demo.repository.AbstractRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;

@ApplicationScoped
public class MemberRepository extends AbstractRepository<Member> {

	@Inject
	private EntityManager em;

	@Inject
	private JPAQueryFactory jpaQueryFactory;

	// public Member findById(Long id) {
	// return em.find(Member.class, id);
	// }

	@Override
	public Member findById(Long id) {
		return find(Member.class, id);
	}

	public Member save(Member tweet) {
		return persistOrMerge(tweet);
	}

	public List<Member> findAll(Predicate expr) {
		return selectFrom(member).where(expr).fetch();
	}

	public Member findByEmail(String email) {
		// CriteriaBuilder cb = em.getCriteriaBuilder();
		// CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
		// Root<Member> member = criteria.from(Member.class);
		// // Swap criteria statements if you would like to try out type-safe
		// // criteria queries, a new
		// // feature in JPA 2.0
		// // criteria.select(member).where(cb.equal(member.get(Member_.name),
		// // email));
		// criteria.select(member).where(cb.equal(member.get("email"), email));
		// return em.createQuery(criteria).getSingleResult();
		return selectFrom(member).where(member.email.equalsIgnoreCase(email)).fetchOne();
		// return findAll(member.email.equalsIgnoreCase(email)).;
	}

	public List<Member> findAllOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
		Root<Member> member = criteria.from(Member.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		criteria.select(member).orderBy(cb.asc(member.get("name")));
		return em.createQuery(criteria).getResultList();
	}
}
