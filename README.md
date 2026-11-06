<p align="center">
  <img src="docs/logo.png" alt="Turbo Hopper" width="200">
</p>

# Turbo Hopper

Faster hoppers and droppers, with a toggle button right in their GUI.

**Minecraft 1.20.1 / Forge 47+** — required on both the client and the server.

[日本語はこちら](#turbo-hopper日本語)

## Features

### Hopper
- Switch between **Normal** and **Fast** transfer speed from the hopper GUI.
- Fast mode cuts the transfer cooldown from 8 ticks down to 1 tick.

### Dropper
- Switch between **Normal** (redstone-triggered, vanilla behavior) and **Instant** mode from the dropper GUI.
- In Instant mode, a dropper ejects items **as long as it has any**, with no redstone signal needed.
- Instant mode ejects up to 8 items per tick, so a whole stack placed in by hand is emptied quickly.
- If the target container is full, the dropper keeps retrying (every 0.5 seconds) and resumes by itself once there is free space.

## How to use

1. Right-click a hopper or dropper to open its GUI.
2. Press the button on the right side of the GUI to switch the mode.
3. The mode is stored in the block, so it is kept when the world is saved and reloaded.

Dispensers are not affected.

## Installation

1. Install Minecraft Forge for 1.20.1 (47.x or newer).
2. Download the jar from [Releases](../../releases) (or CurseForge: _link coming soon_).
3. Put the jar in your `mods` folder — on **both** the client and the server.

## Building from source

```
gradlew build
```

The jar will be created in `build/libs/`.

## Compatibility notes

- This mod adds one block state property (`normal_speed`) to the vanilla hopper and dropper using Mixin.
- Mods that replace or heavily modify hoppers and droppers may not show the toggle button.
- Please report any compatibility problems in [Issues](../../issues) with your Minecraft / Forge versions, a list of other mods, and `latest.log`.

## License

[MIT](LICENSE)

---

# Turbo Hopper(日本語)

ホッパーとドロッパーを高速化し、GUIのボタンで切り替えられるようにするForge用MODです。

**Minecraft 1.20.1 / Forge 47以降**。クライアントとサーバーの**両方**に導入が必要です。

## 機能

### ホッパー
- ホッパーのGUIのボタンで、**通常 / 高速**を切り替えられます。
- 高速モードでは、転送の待ち時間が8tickから1tickになります。

### ドロッパー
- ドロッパーのGUIのボタンで、**通常**(レッドストーン式、バニラと同じ)と**即排出**を切り替えられます。
- 即排出モードでは、中にアイテムがある間は、レッドストーンの信号なしで排出し続けます。
- 即排出モードでは、1tickに最大8個を排出するので、手で入れたスタックもすぐに空になります。
- 排出先が満杯の時は、0.5秒おきに再試行し、空きができれば自動で再開します。

## 使い方

1. ホッパーまたはドロッパーを右クリックして、GUIを開きます。
2. GUIの右側のボタンを押して、モードを切り替えます。
3. モードはブロックに保存されるので、ワールドを保存して開き直しても保たれます。

ディスペンサーには影響しません。

## 導入方法

1. Minecraft 1.20.1用のForge(47以降)を導入します。
2. [Releases](../../releases)からjarをダウンロードします(CurseForgeは準備中です)。
3. jarを `mods` フォルダに入れます。クライアントとサーバーの**両方**に必要です。

## 互換性について

- Mixinを使って、バニラのホッパーとドロッパーに、ブロックステートのプロパティ(`normal_speed`)を1つ追加しています。
- ホッパーやドロッパーを置き換えたり大きく変更するMODとは、ボタンが表示されない場合があります。
- 不具合は[Issues](../../issues)へ、MinecraftとForgeのバージョン、導入している他のMOD、`latest.log` を添えて報告してください。

## ライセンス

[MIT](LICENSE)