export interface IPlayerMessage {
  id?: number;
  message?: any;
  attack?: string;
  heal?: number;
  difficulty?: number;
  success?: boolean;
  attribute?: string;
  playerId?: number;
  eventId?: number;
}

export const defaultValue: Readonly<IPlayerMessage> = {
  success: false
};
