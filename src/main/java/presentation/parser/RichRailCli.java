package presentation.parser;

import java.util.List;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import domain.RollingStock;
import domain.Train;
import domain.Wagon;
import domain.locomotive.Locomotive;
import parser.RichRailBaseListener;
import parser.RichRailParser;
import persistence.TrainDao;
import persistence.TrainPostgresDaoImpl;
import persistence.WagonDao;
import persistence.WagonPostgresDaoImpl;
import presentation.WagonDirector;
import service.locomotiveFactory.LocomotiveFactory;
import service.wagonBuilder.CustomWagonBuilder;

public class RichRailCli extends RichRailBaseListener {

	private String type;
	private WagonDao wpdi = WagonPostgresDaoImpl.getInstance();
	private TrainDao tpdi = TrainPostgresDaoImpl.getInstance();
	private LocomotiveFactory locomotiveFactory = new LocomotiveFactory();

	@Override
	public void enterCommand(RichRailParser.CommandContext ctx) {

		// GAAT AF BIJ ELK COMMAND DUS OOK HIER KAN OVERLAPPENDE FUNCTIONALITEIT
	}

	@Override
	public void enterNewcommand(RichRailParser.NewcommandContext ctx) {

		// GAAT AF BIJ NEW TRAIN EN NEW WAGON DUS HIER KAN OVERLAPPENDE
		// FUNCTIONALITEIT/attributen definieren om zo minder vaak get calls te doen
	}

	@Override
	public void enterNewtraincommand(RichRailParser.NewtraincommandContext ctx) {

		String id= ctx.getChild(2).toString();
		String engineType = ctx.getChild(4).toString();
		Locomotive engine = locomotiveFactory.createLocomotive(engineType);
		Train train = new Train();
		train.setId(id);
		train.setEngine(engine);
		if(tpdi.save(train)) {
			System.out.println("train " + id + " created");
		}else {
			System.out.println("train "+id+"already exists");
			System.out.println("engine bestaat niet");
		}



	}

	@Override
	public void enterNewwagoncommand(RichRailParser.NewwagoncommandContext ctx) {
		String type = ctx.getChild(2).toString();
		String stringSeats = ctx.getChild(4).toString();
		int seats = Integer.parseInt(stringSeats);
		WagonDirector wagonDirector = new WagonDirector(new CustomWagonBuilder(type, seats));
		wagonDirector.makeWagon();
		Wagon wagon = wagonDirector.getWagon();
		if(wpdi.save(wagon)) {
			System.out.println("wagon " + type + " created with " + stringSeats + " seats");
		}else {
			System.out.println("wagon " + type + " already exists");
		}
		

	}

	@Override
	public void exitAddcommand(RichRailParser.AddcommandContext ctx) {
		String id = ctx.getChild(4).toString();
		String rollingId = ctx.getChild(2).toString();
		Train train = tpdi.findById(id);
		RollingStock r;
		if(type.equals("wagon")) {
			r = wpdi.findByType(rollingId);
		}else if(type.equals("locomotive")) {
			LocomotiveFactory factory = new LocomotiveFactory();
			r = factory.createLocomotive(rollingId);
		}else {
			r = null;
		}
		List<RollingStock> rollingStock = train.getAllRollingStock();
		System.out.println("rollingstock :"+r.getType());
		rollingStock.add(r);
		train.setAllRollingStock(rollingStock);
		tpdi.update(train);
		System.out.println(type+ " " + ctx.getChild(2) + " added to train " + id);
	}

	@Override
	public void exitGetcommand(RichRailParser.GetcommandContext ctx) {
		String id = ctx.getChild(2).toString();
		
		if(type.equals("train")) {
			Train train = tpdi.findById(id);
			System.out.println(train.getSeats());
		}else if(type.equals("wagon")) {
			Wagon wagon = wpdi.findByType(id);
			System.out.println(wagon.getSeats());
			
		}else if(type.equals("locomotive")){
			LocomotiveFactory factory = new LocomotiveFactory();
			Locomotive locomotive = factory.createLocomotive(id);
			System.out.println(locomotive.getSeats());
			
		}

	}

	@Override
	public void exitDelcommand(RichRailParser.DelcommandContext ctx) {
		String id = ctx.getChild(2).toString();
		if(tpdi.delete(id)) {
			System.out.println(ctx.getChild(1).getChild(0)+" "+id+" deleted");
		}else {
			System.out.println(id+" does not exist");
		}
	}

	@Override
	public void exitRemcommand(RichRailParser.RemcommandContext ctx) {
		String id = ctx.getChild(3).toString();
		String stringNum = ctx.getChild(1).toString();
		int num = Integer.parseInt(stringNum);
		Train train = tpdi.findById(id);
		List<RollingStock> rollingStock = train.getAllRollingStock();
		rollingStock.remove(num);
		System.out.println("rollingstock: ");
		for(RollingStock r1: rollingStock) {
			System.out.println(r1.getType());
		}
		train.setAllRollingStock(rollingStock);
		tpdi.update(train);
		System.out.println("rolling component " + stringNum + " removed from train " + id);
	}

	@Override
	public void enterType(RichRailParser.TypeContext ctx) {
		type=ctx.getChild(0).toString();
	}
	
	@Override public void visitTerminal(TerminalNode node) { 
		System.out.println(node);
	}

	@Override
	public void visitErrorNode(ErrorNode node) {
		System.out.println(node);
	}

}
