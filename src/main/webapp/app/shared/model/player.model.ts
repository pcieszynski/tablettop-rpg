import { IGamemaster } from 'app/shared/model//gamemaster.model';
import { IPlayerMessage } from 'app/shared/model//player-message.model';
import { IGame } from 'app/shared/model//game.model';

export interface IPlayer {
  id?: number;
  username?: string;
  keycloakId?: string;
  gamemasters?: IGamemaster[];
  playerMessages?: IPlayerMessage[];
  games?: IGame[];
}

export const defaultValue: Readonly<IPlayer> = {};
