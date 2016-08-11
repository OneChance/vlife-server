package app.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Table;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import app.account.entity.Account;

@Component
public class DatabaseService {
	@Autowired
	public DatabaseService(JdbcTemplate jdbcTemplate, EntityManagerFactory factory) {
		this.jdbcTemplate = jdbcTemplate;
		if (factory.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		this.sessionFactory = factory.unwrap(SessionFactory.class);
	}

	private JdbcTemplate jdbcTemplate;
	private SessionFactory sessionFactory;

	public <T> void save(final T t) throws Exception {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(t);
			session.getTransaction().commit();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public <T> void save(final List<T> ts) throws Exception {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			if (null != ts) {
				for (T t : ts) {
					session.save(t);
				}
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public <T> void merge(final List<T> ts) throws Exception {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			if (null != ts) {
				for (T t : ts) {
					session.merge(t);
				}
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public <T> Object merge(final T t) throws Exception {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Object o = session.merge(t);
			session.getTransaction().commit();
			return o;
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public <T> void update(final T t) throws Exception {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(t);
			session.getTransaction().commit();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public <T> void delete(T t) throws Exception {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.delete(t);
			session.getTransaction().commit();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public <T> void update(Class<T> c, final String sql, final Object[] obs) {
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				if (null != obs && obs.length > 0) {
					for (int n = 0; n < obs.length; n++) {
						ps.setObject(n + 1, obs[n]);
					}
				}
				return ps;
			}
		});
	}

	public <T> T getForType(String sql, Class<T> c) {
		return jdbcTemplate.queryForObject(sql, c);
	}

	public <T> T get(Class<T> c, Serializable id) throws Exception {
		T t = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			t = (T) session.get(c, id);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return t;
	}

	public <T> T get(Class<T> c, final String sql, final Object[] obs) throws Exception {
		List<T> list = this.gets(c, sql, obs);
		if (null == list) {
			return null;
		}
		if (list.size() == 1) {
			return list.get(0);
		}
		throw new Exception("error");
	}

	public <T> List<T> gets(Class<T> c) {
		return this.gets(c, new StringBuffer("select * from ").append(this.getTableName(c)).toString(), null);
	}

	public <T> List<T> gets(Class<T> c, final String sql, final Object[] obs) {
		List<T> list = jdbcTemplate.query(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				if (null != obs && obs.length > 0) {
					for (int n = 0; n < obs.length; n++) {
						ps.setObject(n + 1, obs[n]);
					}
				}
				return ps;
			}
		}, BeanPropertyRowMapper.newInstance(c));
		if (null == list || list.size() < 1) {
			return null;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getsByHibernate(Class<T> c, final String sql, final Object[] obs) throws Exception {
		List<T> t = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(sql);

			if (null != obs && obs.length > 0) {
				for (int n = 0; n < obs.length; n++) {
					query.setParameter(0, obs[0]);
				}
			}

			t = query.list();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return t;
	}

	public void execute(String sql) {
		Logger.getLogger(getClass()).info(sql);
		this.jdbcTemplate.execute(sql);
	}

	public void execute(String sql, Object[] obs) {
		Logger.getLogger(getClass()).info(sql);
		this.jdbcTemplate.update(sql, obs);
	}

	public <T> boolean checkPersist(Class<T> c) {
		try {
			jdbcTemplate.execute("select * from " + this.getTableName(c));
		} catch (Exception e2) {
			if (e2.getMessage().contains("doesn't exist")) {
				return false;
			} else {
				throw new RuntimeException(e2);
			}
		}
		return true;
	}

	public List<Account> jdbcGetById(Long id) {
		return jdbcTemplate.query("select * from account where id = " + id,
				new BeanPropertyRowMapper<Account>(Account.class));
	}

	public <T> String getTableName(Class<T> c) {
		return ((Table) c.getAnnotation(Table.class)).name();
	}
}