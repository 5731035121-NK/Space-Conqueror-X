package utility;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import entity.IRenderable;

public class RenderManager {
	
	private static RenderManager instance = new RenderManager();
	public static RenderManager getInstance() {
		return instance;
	}
	
	private CopyOnWriteArrayList<IRenderable> renderableList;
	
	public RenderManager() {
		renderableList = new CopyOnWriteArrayList<IRenderable>();
	}
	
	public List<IRenderable> getRenderableList() {
		return renderableList;
	}
	
	public void add(IRenderable obj) {
		renderableList.add(obj);
		Collections.sort(renderableList, new Comparator<IRenderable>() {
			@Override
			public int compare(IRenderable o1, IRenderable o2) {
				if (o1.getZ() > o2.getZ())
					return 1;
				return -1;
			}
		});
	}
	
	public void clear() {
		renderableList.clear();
	}
	
}
