import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Callable;

interface Validatable<R, T> {
  R validate(T arg);
}

class Person {
  private Integer id;
  private String name;
  private Integer age;

  public Person(
    Integer id, 
    String name, 
    Integer age
  ) {
    this.setId(id);
    this.setName(name);
    this.setAge(age);
  }

  public Integer getId() {
    return this.id;
  }
  public String getName() {
    return this.name;
  }
  public Integer getAge() {
    return this.age;
  }

  public void setId(Integer id) {
    this.id = id;
  }
  public void setName(String name) {
    this.name = name;
  }
  public void setAge(Integer age) {
    this.age = age;
  }

  @Override
	public boolean equals(Object obj) {
		List<Validatable<Boolean, Object>> validations = new ArrayList<>();

    validations.add(arg -> !(arg == null));

    validations.add(arg -> {
      return !(arg instanceof Person);
    });

    validations.add((arg) -> {
      Person curso = (Person) arg;	
      return !(this.getId() == null && curso.getId() != null);
    });

    validations.add((arg) -> {
      Person curso = (Person) arg;
      return !(this.getId().equals(curso.getId()));
    });

    if(super.equals(obj)){
      return true;
    }

		for(Validatable<Boolean, Object> validation : validations) {
			if(validation.validate(obj)) {
				return false;
			}
		}

		return true;
	}
}
public class Test {
  public static void main(String[] args) {
    Person lucas = new Person(1, "Lucas", 20);

    Object nullObj = null;

    String aString = "Hello world!";

    Person joao = new Person(2, "Joao", 22);

    Person lucasClone = lucas;

    System.out.println("null: " + lucas.equals(nullObj));
    System.out.println("aString: " + lucas.equals(aString));
    System.out.println("joao: " + lucas.equals(joao));
    System.out.println("lucas: " + lucas.equals(lucas));

  }
}
