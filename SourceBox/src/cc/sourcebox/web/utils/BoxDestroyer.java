package cc.sourcebox.web.utils;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import cc.sourcebox.beans.BoxManagerRemote;

public class BoxDestroyer implements HttpSessionBindingListener {

	BoxManagerRemote b;
	public BoxDestroyer(BoxManagerRemote b) {
		this.b = b;
	}
	
	@Override
	public void valueBound(HttpSessionBindingEvent arg0) {}

	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		b.remove();
	}

	
}