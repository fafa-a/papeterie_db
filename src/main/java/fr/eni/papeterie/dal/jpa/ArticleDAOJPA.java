package fr.eni.papeterie.dal.jpa;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import fr.eni.papeterie.entities.ArticleEntity;

public class ArticleDAOJPA {

  private EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistence");
  private EntityManager entityManager = factory.createEntityManager();
  private EntityTransaction transaction = entityManager.getTransaction();

  public void closeEntityManager() {
    entityManager.close();
  }

  public void create(ArticleEntity article) {
    transaction.begin();
    entityManager.persist(article);
    transaction.commit();
  }

  public ArticleEntity selectById(int id) {
    System.out.println("Selection de l'article par son id... " + id);
    return entityManager.find(ArticleEntity.class, id);
  }

  public List<ArticleEntity> selectAll() {
    System.out.println("Selection de touts les articles... ");
    String HQL = "FROM articles";
    return entityManager.createQuery(HQL, ArticleEntity.class).getResultList();

  }

  public List<ArticleEntity> selectByMarque(String marque) {
    System.out.println("Selection par la marque... " + marque);
    String HQL = "FROM articles WHERE LOWER(marque) = :marque";
    return entityManager.createQuery(HQL, ArticleEntity.class).setParameter("marque", marque.toLowerCase())
        .getResultList();

  }

  public List<ArticleEntity> selectByMotCle(String motCle) {
    System.out.println("Selection par le mot clé... " + motCle);
    String HQL = "FROM articles WHERE LOWER(marque) = :motCle OR LOWER(designation) LIKE :motCle ";
    return entityManager.createQuery(HQL, ArticleEntity.class).setParameter("motCle", "%" + motCle.toLowerCase() + "%")
        .getResultList();

  }

  public void update(ArticleEntity article) {
    System.out.println("Mise à jour de l'article... ");
    transaction.begin();
    entityManager.merge(article);
    transaction.commit();

  }

  public void delete(ArticleEntity article) {
    System.out.println("Suppression de l'article... ");
    ArticleEntity articleToDelete = entityManager.find(ArticleEntity.class, article.getIdArticle());
    transaction.begin();
    entityManager.remove(articleToDelete);
    transaction.commit();

  }
}
