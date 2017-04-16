package com.broodcamp.javaee_querydsl_demo.repository;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.broodcamp.javaee_querydsl_demo.model.Identifiable;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.HQLTemplates;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;

public abstract class AbstractRepository<T extends Identifiable> implements Repository<T, Long> {

	@Inject
	private Provider<EntityManager> em;

	@SuppressWarnings("hiding")
	protected <T> JPAQuery<T> selectFrom(EntityPath<T> entity) {
		return select(entity).from(entity);
	}

	@SuppressWarnings("hiding")
	protected <T> JPAQuery<T> select(Expression<T> select) {
		return new JPAQuery<>(em.get(), HQLTemplates.DEFAULT).select(select);
	}

	protected JPADeleteClause delete(EntityPath<?> entity) {
		return new JPADeleteClause(em.get(), entity, HQLTemplates.DEFAULT);
	}

	protected void detach(Object entity) {
		em.get().detach(entity);
	}

	protected <E> E find(Class<E> type, Long id) {
		return em.get().find(type, id);
	}

	protected void persist(Object entity) {
		em.get().persist(entity);
	}

	protected <E> E merge(E entity) {
		return em.get().merge(entity);
	}

	protected <E extends Identifiable> E persistOrMerge(E entity) {
		if (entity.getId() != null) {
			return merge(entity);
		}
		persist(entity);
		return entity;
	}

	protected void remove(Object entity) {
		em.get().remove(entity);
	}

}
