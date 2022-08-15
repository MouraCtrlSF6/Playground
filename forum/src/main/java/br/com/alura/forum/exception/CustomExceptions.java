package br.com.alura.forum.exception;

public interface CustomExceptions {
	static class UserIdNotFound extends Exception {
		private Long value;
		private String field;
		
		public IdNotFound(Long value, String field) {
			this.value = value;
			this.field = field;
		}
		
		@Override
		public String getMessage() {
			return "
		}
	}
}
