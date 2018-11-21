import { IEvent } from 'app/shared/model//event.model';
import { IPlayer } from 'app/shared/model//player.model';
import { ICharacter } from 'app/shared/model//character.model';

export interface IGame {
  id?: number;
  currentEvent?: number;
  currentPlayer?: string;
  events?: IEvent[];
  players?: IPlayer[];
  gamemasterId?: number;
  characters?: ICharacter[];
}

export const defaultValue: Readonly<IGame> = {};
