connect 'jdbc:derby://localhost:1527/testing;create=true;user=root;password=root';
SELECT users.login, tests.name, subjects.name, tests.complexity_id, tests.timer, users_tests.result
			FROM tests, subjects, users, users_tests
			WHERE subjects.id=tests.subject_id AND users.id=users_tests.user_id AND tests.id=users_tests.test_id;
