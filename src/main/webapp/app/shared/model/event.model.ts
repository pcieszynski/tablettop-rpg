import { INpc } from 'app/shared/model//npc.model';
import { IBattle } from 'app/shared/model//battle.model';
import { IPlayerMessage } from 'app/shared/model//player-message.model';

export interface IEvent {
  id?: number;
  name?: string;
  description?: any;
  npcs?: INpc[];
  battles?: IBattle[];
  playerMessages?: IPlayerMessage[];
  gameId?: number;
  shopId?: number;
}

export const defaultValue: Readonly<IEvent> = {};
