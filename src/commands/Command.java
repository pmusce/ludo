package commands;

import java.io.Serializable;

public abstract class Command implements Serializable {
	private static final long serialVersionUID = 1L;

	public abstract void execute();
}
