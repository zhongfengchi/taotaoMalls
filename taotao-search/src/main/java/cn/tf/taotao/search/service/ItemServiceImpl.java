package cn.tf.taotao.search.service;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import cn.tf.taotao.common.utils.TaotaoResult;
import cn.tf.taotao.search.mapper.ItemMapper;
import cn.tf.taotao.search.pojo.Item;

public class ItemServiceImpl implements ItemService{

	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private SolrServer  solrServer;
	
	@Override
	public TaotaoResult importAllItems() throws SolrServerException, IOException {
		List<Item> list=itemMapper.searchItemList();
		
		
		for (Item item : list) {
			SolrInputDocument  document=new SolrInputDocument();
			document.setField("id", item.getId());
			document.setField("item_title", item.getTitle());
			document.setField("item_sell_point", item.getSell_point());
			document.setField("item_price", item.getPrice());
			document.setField("item_image", item.getImage());
			document.setField("item_category_name", item.getCategory_name());
			document.setField("item_desc", item.getItem_des());
			
			
			//将文档写入索引库
		
			solrServer.add(document);
		

		}
		solrServer.commit();
		return TaotaoResult.ok();
	}

}
