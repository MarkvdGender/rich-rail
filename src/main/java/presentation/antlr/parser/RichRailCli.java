package presentation.antlr.parser;

import parser.RichRailBaseListener;
import parser.RichRailParser;
import service.locomotive.LocomotiveService;
import service.train.TrainService;
import service.train.trainobserver.TrainSubject;
import service.wagon.WagonService;

public class RichRailCli extends RichRailBaseListener {

	private String type;
	private static TrainSubject subject = TrainSubject.getInstance();
	private TrainService trainService = new TrainService();
	private WagonService wagonService = new WagonService();
	private LocomotiveService locomotiveService = new LocomotiveService();

	@Override
	public void exitNewtraincommand(RichRailParser.NewtraincommandContext ctx) {
		String id = ctx.getChild(2).toString();
		String engine = ctx.getChild(4).toString();

		if (ctx.getChildCount() == 7) {
			String oldId = ctx.getChild(6).toString();
			subject.cloneTrain(oldId, id);

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
			trainService.getSeats(id);
		} else if (type.equals("wagon")) {
			wagonService.getSeats(id);

		} else if (type.equals("locomotive")) {
			locomotiveService.getSeats(id);

		}

	}

	@Override
	public void exitDelcommand(RichRailParser.DelcommandContext ctx) {
//		type = ctx.getChild(1).getChild(0).toString();
		String id = ctx.getChild(2).toString();
		if (type.equals("train")) {
			subject.deleteTrain(id);
		} else if (type.equals("wagon")) {
			subject.deleteWagon(id);

		}

	}

	@Override
	public void exitRemcommand(RichRailParser.RemcommandContext ctx) {
		String id = ctx.getChild(3).toString();
		String stringNum = ctx.getChild(1).toString();
		int index = Integer.parseInt(stringNum);
		subject.removeRollingStock(index, id);

	}

	@Override
	public void enterType(RichRailParser.TypeContext ctx) {
		type = ctx.getChild(0).toString();
	}

}
