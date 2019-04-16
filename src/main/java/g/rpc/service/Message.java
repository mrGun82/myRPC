package g.rpc.service;

import java.io.Serializable;

public class Message  implements Serializable{
	
	private static final long serialVersionUID = -3884743728058688185L;
	private String name;
	private String content;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Message [name=" + name + ", content=" + content + "]";
	}
}
