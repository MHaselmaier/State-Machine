package framework;

public class Event<T> {
	
	private T value;
	
	public Event(final T aValue) {
		this.value = aValue;
	}
	
	@Override
	public boolean equals(final Object anOther) {
		if(this == anOther) return true;
		if(null == anOther) return false;
		if(!(anOther instanceof Event<?>)) return false;
		
		Event<?> other = (Event<?>)anOther;
		return get().equals(other.get());
	}
	
	public T get() {
		return this.value;
	}
	
}