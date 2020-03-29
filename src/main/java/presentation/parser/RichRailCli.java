package presentation.parser;

import java.util.List;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import domain.RollingStock;
import domain.Train;
import domain.Wagon;
import domain.locomotive.Locomotive;
import observer.train.TrainSubject;
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
	private static TrainSubject subject = TrainSubject.getInstance();

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
	public void exitNewtraincommand(RichRailParser.NewtraincommandContext ctx) {
		String id = ctx.getChild(2).toString();
		String engine = ctx.getChild(4).toString();

		if (ctx.getChildCount() == 7) {
			String oldId = ctx.getChild(6).toString();
			subject.cloneTrain(subject.findTrain(oldId), id);

		} else {
			subject.newTrain(id, engine);
		}

	}

	@Override
	public void enterNewwagoncommand(RichRailParser.NewwagoncommandContext ctx) {
		String type = ctx.getChild(2).toString();
		String stringSeats = ctx.getChild(4).toString();
		int seats = Integer.parseInt(stringSeats);
		subject.newWagon(type, seats);

	}

	@Override
	public void exitAddcommand(RichRailParser.AddcommandContext ctx) {
		String id = ctx.getChild(4).toString();
		String rollingId = ctx.getChild(2).toString();
		if (type.equals("wagon")) {
			subject.addWagonToTrain(id, rollingId);
		} else if (type.equals("locomotive")) {
			subject.addLocomotiveToTrain(id, rollingId);
		}
	}

	@Override
	public void exitGetcommand(RichRailParser.GetcommandContext ctx) {
		String id = ctx.getChild(2).toString();

		if (type.equals("train")) {
			Train train = subject.findTrain(id);
			System.out.println(train.getSeats());
		} else if (type.equals("wagon")) {
			Wagon wagon = subject.findWagon(id);
			System.out.println(wagon.getSeats());

		} else if (type.equals("locomotive")) {
			Locomotive locomotive = subject.findLocomotive(id);
			System.out.println(locomotive.getSeats());

		}

	}

	@Override
	public void exitDelcommand(RichRailParser.DelcommandContext ctx) {
		String id = ctx.getChild(2).toString();
		subject.deleteTrain(id);

	}

	@Override
	public void exitRemcommand(RichRailParser.RemcommandContext ctx) {
		String id = ctx.getChild(3).toString();
		String stringNum = ctx.getChild(1).toString();
		int index = Integer.parseInt(stringNum);
		Train train = subject.findTrain(id);
		train.removeRollingStock(index);
	
	}

	@Override
	public void enterType(RichRailParser.TypeContext ctx) {
		type = ctx.getChild(0).toString();
	}

	@Override
	public void visitTerminal(TerminalNode node) {
		System.out.println(node);
	}

	@Override
	public void visitErrorNode(ErrorNode node) {
		System.out.println(node);
	}

}
